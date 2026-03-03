# Shift4 Java Library

If you don't already have Shift4 account you can create it [here](https://dev.shift4.com/signup). 

## Installation 

### Maven

Best way to use this library is via [Maven](https://maven.apache.org).

To do this you will need to add this configuration to your `pom.xml`:

```xml
<dependency>
    <groupId>com.shift4</groupId>
    <artifactId>shift4-java</artifactId>
    <version>3.5.0</version>
</dependency>
```

### Manual installation

If you don't want to use Maven then you can download [the latest release](https://github.com/shift4developer/shift4-java/releases).

## Quick start example

```java
Shift4Gateway gateway = new Shift4Gateway("sk_test_[YOUR_SECRET_KEY]");

ChargeRequest request = new ChargeRequest()
		.amount(499)
		.currency("EUR")
		.card(new CardRequest()
				.number("4242424242424242")
				.expMonth("11")
				.expYear("2022"));

try {
    Charge charge = gateway.createCharge(request);

    // do something with charge object - see https://dev.shift4.com/docs/api#charge-object
    String chargeId = charge.getId();

} catch (Shift4Exception e) {
    // handle error response - see https://dev.shift4.com/docs/api#error-object
    ErrorType errorType = e.getType();
    ErrorCode errorCode = e.getCode();
    String errorMessage = e.getMessage();
}
```


## Documentation


For further information, please refer to our official documentation at https://dev.shift4.com/docs.
