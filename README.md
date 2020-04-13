# The nurse scheduling problem (NSP) implimentation with choco solver 

The nurse scheduling problem (NSP), also called the nurse rostering problem (NRP), is the operations research problem of finding
an optimal way to assign nurses to shifts, typically with a set of hard constraints which all valid solutions must follow, and a 
set of soft constraints which define the relative quality of valid solutions.Solutions to the nurse scheduling problem 
can be applied to constrained scheduling problems in other fields.

The nurse scheduling problem has been studied since before 1969,and is known to have NP-hard complexity.

Such a schedule must respect certain rules including resulting from the regulations and the agreements agreed between managers 
and nurses. Specifically, theplanning must satisfy the following constraints:
 * C1. For each day t, station j must contain as many nurses as necessary
    * • a nurse for each position 1 and 6,
    * • two to three nurses for the three activities, namely surgery consultation (2), care (3) and plaster (4),
    * • one to two nurses in the break-out room (5),
    * • in the case of 6 nurses available, two nurses are assigned to the break-out room (5).

* C2. The nurse must work for a working day and when he is not on vacation.

* C3. The nurse who works on the public holiday (tf) takes rest on the two public holidays following t (f + 1) and t (f + 2).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisities

What things you need to install the software and how to install them

    JDK 8+
    choco-solver-4.10.2.jar

## Author

* **Karim EL KHEDDOUMI** 
