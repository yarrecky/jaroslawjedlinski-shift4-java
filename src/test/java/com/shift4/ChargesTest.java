package com.shift4;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.shift4.enums.ChargeStatus;
import com.shift4.enums.ChargeType;
import com.shift4.enums.ErrorType;
import com.shift4.exception.Shift4Exception;
import com.shift4.request.ChargeListRequest;
import com.shift4.request.ChargeRequest;
import com.shift4.request.RequestOptions;
import com.shift4.response.Charge;
import com.shift4.response.Customer;
import com.shift4.response.ListResponse;
import com.shift4.utils.ListResponseUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.shift4.enums.ErrorCode.INCORRECT_CVC;
import static com.shift4.testdata.Cards.incorrectCvcCard;
import static com.shift4.testdata.Cards.successCard;
import static com.shift4.testdata.Charges.charge;
import static com.shift4.testdata.Customers.customer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.throwable;

class ChargesTest extends AbstractShift4GatewayTest {

    @Test
    void shouldCreateCharge() {
        SerializationFeature a = WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;
        // given
        ChargeRequest request = charge().card(successCard());
        // when
        Charge charge = gateway.createCharge(request);
        // then
        assertThat(charge.getStatus()).isEqualTo(ChargeStatus.SUCCESSFUL);
        assertThat(charge.getAmount()).isEqualTo(request.getAmount());
        assertThat(charge.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(charge.getFailureCode()).isNull();
    }

    @Test
    void shouldReadFailureProperties() {
        // given
        ChargeRequest request = charge().card(incorrectCvcCard());
        // when
        assertThatThrownBy(() -> gateway.createCharge(request))
                // then
                .asInstanceOf(throwable(Shift4Exception.class))
                .satisfies(exception -> {
                    assertThat(exception.getCode()).isEqualTo(INCORRECT_CVC);
                    assertThat(exception.getType()).isEqualTo(ErrorType.CARD_ERROR);
                    assertThat(exception.getChargeId()).matches("char_\\w+");
                    assertThat(exception.getCreditId()).isNull();
                });
    }

    @Test
    void shouldRetrieveCharge() {
        // given
        ChargeRequest request = charge().card(successCard());
        Charge created = gateway.createCharge(request);
        // when
        Charge retrieved = gateway.retrieveCharge(created.getId());
        // then
        assertThat(retrieved.getStatus())
                .isEqualTo(created.getStatus())
                .isEqualTo(ChargeStatus.SUCCESSFUL);
        assertThat(retrieved.getAmount())
                .isEqualTo(created.getAmount())
                .isEqualTo(request.getAmount());
        assertThat(retrieved.getCurrency())
                .isEqualTo(created.getCurrency())
                .isEqualTo(request.getCurrency());
        assertThat(retrieved.getFailureCode())
                .isEqualTo(created.getFailureCode())
                .isNull();
    }

    @Test
    void shouldListChargesOfCustomer() {
        // given
        Customer customer = gateway.createCustomer(customer(successCard()));
        ChargeRequest request = charge().customer(customer);

        String charge1 = gateway.createCharge(request).getId();
        String charge2 = gateway.createCharge(request).getId();
        String charge3 = gateway.createCharge(request).getId();
        String charge4 = gateway.createCharge(request).getId();
        String charge5 = gateway.createCharge(request).getId();
        String charge6 = gateway.createCharge(request).getId();

        // when
        ListResponse<Charge> page1 = gateway.listCharges(new ChargeListRequest().customer(customer).limit(2));
        ListResponse<Charge> page2 = gateway.listCharges(new ChargeListRequest().customer(customer).limit(2).startingAfterId(ListResponseUtils.last(page1).getId()));
        ListResponse<Charge> page3 = gateway.listCharges(new ChargeListRequest().customer(customer).limit(2).startingAfterId(ListResponseUtils.last(page2).getId()));

        // then
        assertThat(page1).extracting(Charge::getId).containsExactly(charge6, charge5);
        assertThat(page1.getHasMore()).isTrue();
        assertThat(page2).extracting(Charge::getId).containsExactly(charge4, charge3);
        assertThat(page2.getHasMore()).isTrue();
        assertThat(page3).extracting(Charge::getId).containsExactly(charge2, charge1);
        assertThat(page3.getHasMore()).isFalse();
    }

    @Test
    void shouldCreateChargeOnceWhenIdempotencyKeyIsUsed() {
        // given
        ChargeRequest request = charge().card(successCard());
        String idempotencyKey = UUID.randomUUID().toString();
        RequestOptions requestOptions = RequestOptions.withIdempotencyKey(idempotencyKey);
        // when
        Charge charge = gateway.createCharge(request, requestOptions);
        // then
        assertThat(charge.getStatus()).isEqualTo(ChargeStatus.SUCCESSFUL);
        assertThat(charge.getAmount()).isEqualTo(request.getAmount());
        assertThat(charge.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(charge.getFailureCode()).isNull();
        // and when another call with same idempotency key
        Charge second = gateway.createCharge(request, requestOptions);
        // then
        assertThat(second.getId()).isEqualTo(charge.getId());
    }

    @Test
    void shouldCreateChargeUsingExplicitMerchant() {
        // given
        Shift4Gateway gatewayWithExplicitMerchant = createExplicitMerchantGateway();
        ChargeRequest request = charge().card(successCard());

        // when
        Charge createdCharge = gatewayWithExplicitMerchant.createCharge(request);
        Charge retrievedCharge = gatewayWithExplicitMerchant.retrieveCharge(createdCharge.getId());

        // then
        assertThat(retrievedCharge.getStatus()).isEqualTo(ChargeStatus.SUCCESSFUL);
        assertThat(retrievedCharge.getAmount()).isEqualTo(request.getAmount());
        assertThat(retrievedCharge.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(retrievedCharge.getFailureCode()).isNull();
    }

    @Test
    void shouldSendChargeType() {
        // given
        ChargeRequest request = charge().card(successCard()).type(ChargeType.CUSTOMER_INITIATED);
        // when
        Charge charge = gateway.createCharge(request);
        // then
        assertThat(charge.getType()).isEqualTo(ChargeType.CUSTOMER_INITIATED);
    }

    @Test
    void shouldReturnSchemeTransactionIdInChargeResponse() {
        // given
        ChargeRequest request = charge()
                .card(successCard())
                .external(new ChargeRequest.External()
                        .schemeTransactionId("external-scheme-tx-id"));
        // when
        Charge charge = gateway.createCharge(request);
        // then
        assertThat(charge.getExternal()).isNotNull();
        assertThat(charge.getExternal().getSchemeTransactionId()).isNotNull();
    }

    @Test
    void shouldReturnVendorReferenceInChargeResponse() {
        // given
        String vendorReference = "external-vendor-ref";
        ChargeRequest request = charge()
                .card(successCard())
                .external(new ChargeRequest.External()
                        .vendorReference(vendorReference));
        // when
        Charge charge = gateway.createCharge(request);
        // then
        assertThat(charge.getExternal()).isNotNull();
        assertThat(charge.getExternal().getVendorReference()).isEqualTo(vendorReference);
    }
}