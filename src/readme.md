# Cellular Automaton Workshop

## Overview

CA Workshop allows the user to create a user-defined elementary cellular automaton using a native 
scripting language. 

The language for defining the cellular automaton consists of the following keywords:  
- ``blocksize <size>``: defines the number of cells in a rule block
- ``background <color>``: instructs the engine to initialize the image with the specified color
- ``colors <color1> <color2> … <colorN>``: defines the colors the CA will use
- ``setpixel <X-coordinate> <Y-coordinate> <color>``: turns on a pixel with the specified color before processing rules
- ``dim <width> <height>``: defines the dimensions of the image to be generated
- ``rules``: begins a rule block
- ``endrules``: ends a rule block  

Individual rules are declared in the following format:
- ```[ <lcolor1> <lcolor2> … <lcolorN> ] = [ <rcolor1|_> <rcolor2|_> … <rcolor3|_> ]```  

where the “_” symbol instructs the engine to print nothing in the cell position.   

Any named color defined by JavaFX is allowed, as well as any hexadecimal RGBA color of the form #<hex_number>, where “hex_number” is a 32-bit hexadecimal value.

For example:  

```[ black black black ] = [ _ white _ ]```  

An example script and the image it generates is included below:

```
blocksize 3
background white
colors black white
dim 200 200
setpixel 100 0 black
rules
    [black black black] = [_ white _]
    [black black white] = [_ white _]
    [black white black] = [_ white _]
    [black white white] = [_ black _]
    [white black black] = [_ white _]
    [white black white] = [_ black _]
    [white white black] = [_ black _]
    [white white white] = [_ white _]
endrules
```

## Running CA Workshop

The easiest way to run CA workshop is to clone the repository using IntelliJ IDEA. The repository URL is
https://github.com/dhone-asu/CAWorkshop

