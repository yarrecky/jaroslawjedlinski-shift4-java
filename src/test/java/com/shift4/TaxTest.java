package com.shift4;

import com.shift4.request.TaxListRequest;
import com.shift4.request.TaxRequest;
import com.shift4.request.TaxUpdateRequest;
import com.shift4.response.ListResponse;
import com.shift4.response.Tax;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.shift4.testdata.Taxes.tax;
import static org.assertj.core.api.Assertions.assertThat;

class TaxTest extends AbstractShift4GatewayTest {

    @Test
    void shouldCreateTax() {
        // given
        TaxRequest request = tax();
        // when
        Tax created = gateway.createTax(request);
        // then
        assertThat(created.getId()).isNotBlank();
        assertThat(created.getName()).isEqualTo("VAT");
        assertThat(created.getValue()).isEqualTo(23);
        assertThat(created.getActive()).isTrue();
    }

    @Test
    void shouldRetrieveTax() {
        // given
        Tax created = gateway.createTax(tax());
        // when
        Tax retrieved = gateway.retrieveTax(created.getId());
        // then
        assertThat(created).usingRecursiveComparison().isEqualTo(retrieved);
    }

    @Test
    void shouldUpdateTax() {
        // given
        Tax created = gateway.createTax(tax());
        // when
        TaxUpdateRequest request = new TaxUpdateRequest(created)
                .name("Updated Tax")
                .value(new BigDecimal("10.00"));
        Tax updated = gateway.updateTax(request);
        // then
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Updated Tax");
        assertThat(updated.getValue()).isEqualTo(10);
    }

    @Test
    void shouldListTaxes() {
        // given
        Tax tax1 = gateway.createTax(tax("Tax 1", "5.00"));
        Tax tax2 = gateway.createTax(tax("Tax 2", "10.00"));
        Tax tax3 = gateway.createTax(tax("Tax 3", "15.00"));
        // when
        ListResponse<Tax> response = gateway.listTaxes();
        // then
        assertThat(response.getList())
                .extracting(Tax::getId)
                .containsSequence(tax3.getId(), tax2.getId(), tax1.getId());
    }

    @Test
    void shouldListTaxesWithPagination() {
        // given
        Tax tax1 = gateway.createTax(tax("Pagination 1", "5.00"));
        Tax tax2 = gateway.createTax(tax("Pagination 2", "10.00"));
        Tax tax3 = gateway.createTax(tax("Pagination 3", "15.00"));
        Tax tax4 = gateway.createTax(tax("Pagination 4", "20.00"));
        Tax tax5 = gateway.createTax(tax("Pagination 5", "25.00"));
        // when
        TaxListRequest request = new TaxListRequest()
                .limit(2)
                .startingAfter(tax5);
        ListResponse<Tax> response = gateway.listTaxes(request);
        // then
        assertThat(response.getList()).hasSize(2);
        assertThat(response.getList())
                .extracting(Tax::getId)
                .containsExactly(tax4.getId(), tax3.getId());
    }
}
