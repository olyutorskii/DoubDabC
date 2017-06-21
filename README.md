# DoubDabC #

[![Build Status](https://travis-ci.org/olyutorskii/DoubDabC.svg?branch=master)](https://travis-ci.org/olyutorskii/DoubDabC)

-----------------------------------------------------------------------

## What is DoubDabC ? ##

* **DoubDabC** is a Java library
that supports **binary integer value to decimal sequence conversion**
with alternative algorithm.

* Yes, it will substitute implementations such as
`Integer.toString(int)` and so on.


## DoubDabC implementation ##

* DoubDabC implements **[Double-Dabble algorithm][DDA]**
aka **Shift-and-add-3**.
Double-Dabble is a radix-conversion algorithm
but there is no division\(/\) nor remainder\(%\) operation.
It's a bit fast.

* There is no String constructor during conversion.
That means, **GC-friendry !**


## Supported Input ##

* DoubDabC has a pseudo register of the size you specified.
You can put N-bit bool, `int`, `long`, `BitSet`, or `BigInteger`
as binary integer value.

* Let's try to put a huge Mersenne prime like `2^32582657 -1`.
Overflow ? OK, don't worry.
Just losing higher decimals over you specified.


## Supported Output ##

* You can get each decimal number column result by `int` or `int[]` array.

* You can assign
`Appendable`, `Writer`, `StringBuffer`, `StringBuilder`, or `CharBuffer`
as Arabic numeral characters\(0-9\) sequence output.

* `CharSequence` wrapper class is provided.


## Limitations ##

* DoubDabC does not support negative values.
Signed-values are treated as Unsigned-value
like `Integer.toUnsignedString(int)`.
Let's convert absolute value that follows minus\(-\) sign.


## What is the difference with [**JarabraDix**][JDX] ? ##

* If you use binary integer value that fit in 'long' or 'int',
and you have modern JVM and CPU that supports fast integer division,
let's try **JarabraDix**.


## How to build ##

* DoubDabC needs to use [Maven 3.0.1+](https://maven.apache.org/)
and JDK 1.7+ to be built.

* DoubDabC runtime does not depend on any other library at all.
Just compile Java sources under `src/main/java/` if you don't use Maven.


## License ##

* Code is under [The MIT License][MIT].


## Project founder ##

* By [olyutorskii](https://github.com/olyutorskii) at 2017


## Key technology ##

- [Double-Dabble algorithm][DDA]
- [Binary-coded decimal (Packed BCD)][PBCD]
- [Bi-quinary coded decimal][BQCD]
- [Left-shift operation with freesize pseudo register][LSFT]


[DDA]: https://en.wikipedia.org/wiki/Double_dabble "Double-Dabble algorithm"
[PBCD]: https://en.wikipedia.org/wiki/Binary-coded_decimal#Packed_BCD "Packed Binary coded decimal"
[BQCD]: https://en.wikipedia.org/wiki/Bi-quinary_coded_decimal "Bi-quinary coded decimal"
[LSFT]: https://en.wikipedia.org/wiki/Bitwise_operation#Bit_shifts "Left shift"
[JDX]: https://github.com/olyutorskii/JarabraDix
[MIT]: https://opensource.org/licenses/MIT


--- EOF ---
