## Task

Read the sample code in `src/main/java/sample`.
Read the matching sample tests in `src/test/java/sample`.
Then try to add tests for the classes in `src/main/java/task` as described below.

The sample tests should contain all relevant JUnit and Mockito methods needed to complete the task.

### WeatherParserTest

Create unit tests for the `WeatherParser` class.
You'll first need to change the `WeatherParser` class to make it testable.

Move the code that downloads the forecast xml into a separate class.
If it seems complicated, break it into smaller steps:
* find the code that downloads the forecast xml
* move that code into a new method in `WeatherParser`
* create a new class for downloading the forecast.
  move the downloading method into the new class.
* add a constructor parameter to the `WeatherParser` for providing the downloading class instance

The `WeatherParser` class should work just like originally after following the steps above.
The benefit is that the forecast downloading logic can now be modified by passing a different object to the constructor.

Create a mock forecast downloader for testing by overriding methods in the new forecast downloading class.
The mock shouldn't download anything from the actual *yr.no* weather service.
The `forecast.xml` in src/test/resources should be used instead (see the `readTestForecast` method in `WeatherParserTest`).

Don't use mockito for this task.

Create at least the following tests:
1. findsCorrectTemperatureFromForecast
2. throwsExceptionIfTemperatureNotFoundForGivenDate


### TodoAppTest

Create unit tests for the `TodoApp` class.
Make the class more easily testable by mocking the file system operations.
The `todo.bin` file should not be touched in the tests.
Instead, create a mock that can store the values in-memory (in a simple field).

Don't use mockito for this task.

Create at least the following tests:
1. itemIsInListAfterBeingAdded
2. itemNotInListAfterBeingRemoved
3. addingDuplicateItemDoesNotChangeStoredData

### TicketOfficeTest

Create unit tests for the `TicketOffice` class.
Use Mockito to mock the `TicketDatabase` interface.

Create at least the following tests:
1. throwsExceptionWhenNoSeatsAvailable
2. returnsAvailableSeatOnPurchase
3. callsReserveSeatWithCorrectArguments
