package com.shift4;

import com.shift4.request.AmountRequest;
import com.shift4.request.ProductListRequest;
import com.shift4.request.ProductRequest;
import com.shift4.request.ProductUpdateRequest;
import com.shift4.response.DeleteResponse;
import com.shift4.response.ListResponse;
import com.shift4.response.Product;
import org.junit.jupiter.api.Test;

import static com.shift4.testdata.Products.product;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest extends AbstractShift4GatewayTest {

    @Test
    void shouldCreateProduct() {
        // given
        ProductRequest request = product()
                .description("Test product for CRUD operations");
        // when
        Product created = gateway.createProduct(request);
        // then
        assertThat(created.getId()).isNotBlank();
        assertThat(created.getName()).isEqualTo(request.getName());
        assertThat(created.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(created.getAmount()).isEqualTo(request.getAmount().getValue());
        assertThat(created.getDescription()).isEqualTo(request.getDescription());
        assertThat(created.getActive()).isTrue();
        assertThat(created.getDeleted()).isFalse();
    }

    @Test
    void shouldRetrieveProduct() {
        // given
        Product created = gateway.createProduct(product());
        // when
        Product retrieved = gateway.retrieveProduct(created.getId());
        // then
        assertThat(created).usingRecursiveComparison().isEqualTo(retrieved);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product created = gateway.createProduct(product());
        // when
        ProductUpdateRequest request = new ProductUpdateRequest(created)
                .name("Updated Product Name")
                .description("Updated description")
                .currency(created.getCurrency())
                .amount(new AmountRequest(2500));
        Product updated = gateway.updateProduct(request);
        // then
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Updated Product Name");
        assertThat(updated.getDescription()).isEqualTo("Updated description");
        assertThat(updated.getAmount()).isEqualTo(2500);
    }

    @Test
    void shouldDeleteProduct() {
        // given
        Product created = gateway.createProduct(product());
        // when
        DeleteResponse deleteResponse = gateway.deleteProduct(created.getId());
        Product deleted = gateway.retrieveProduct(created.getId());
        // then
        assertThat(created.getDeleted()).isFalse();
        assertThat(deleteResponse.getId()).isEqualTo(created.getId());
        assertThat(deleted.getDeleted()).isTrue();
    }

    @Test
    void shouldListProducts() {
        // given
        Product product1 = gateway.createProduct(product("Product 1"));
        Product product2 = gateway.createProduct(product("Product 2"));
        Product product3 = gateway.createProduct(product("Product 3"));
        // when
        ListResponse<Product> response = gateway.listProducts();
        // then
        assertThat(response.getList())
                .extracting(Product::getId)
                .containsSequence(product3.getId(), product2.getId(), product1.getId());
    }

    @Test
    void shouldListProductsWithPagination() {
        // given
        Product product1 = gateway.createProduct(product("Pagination Test 1"));
        Product product2 = gateway.createProduct(product("Pagination Test 2"));
        Product product3 = gateway.createProduct(product("Pagination Test 3"));
        Product product4 = gateway.createProduct(product("Pagination Test 4"));
        Product product5 = gateway.createProduct(product("Pagination Test 5"));
        // when
        ProductListRequest request = new ProductListRequest()
                .limit(2)
                .startingAfter(product5);
        ListResponse<Product> response = gateway.listProducts(request);
        // then
        assertThat(response.getList()).hasSize(2);
        assertThat(response.getList())
                .extracting(Product::getId)
                .containsExactly(product4.getId(), product3.getId());
    }

}
