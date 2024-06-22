### What can the System do?

1. Rent vehicles from a list of available vehicles.
2. Return a vehicle if, you're the customer that rented(If someone that hasn't rented tries to return it won't work)
3. Print available vehicles and Rented out vehicles
4. Generate an Invoice when vehicle is returned.

### Approach

The system stores Available and Rented out vehicles. These two datastrucutres never hold the same vehicles. When someone rents a vehicle, the system stores the list of vehicle id's the person has rented and removes them when they return the vehicles.

### Known issues

1. Invoice is hardcoded(there's instructions how to change the dates in the handler)the dates are chosen before compilation, so they're technically harded. -> Future update Customer will hold his Invoice dates and there won't be issues.
