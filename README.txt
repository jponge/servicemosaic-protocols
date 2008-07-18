Notes
=====

The library requires UPPAAL to function. Due to licensing restrictions, you
need to obtain it along with a proper license from http://uppaal.com/

The EmptinessTester class constructor takes the path to the 'verifyta'
executable as its input. If null, then you can define an environment
variable named VERIFYTA that points to the path of this executable.
Alternatively, you may pass the option -Dverifyta=<path> to the JVM.
