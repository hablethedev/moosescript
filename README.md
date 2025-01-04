# Moosescript

## Introduction

**Moosescript** is a hard fork of Minescript where you can control and interact with Minecraft using scripts written in
Python. It is implemented as mod for [Fabric](https://fabricmc.net/).

The examples below require Moosescript 1.0 or higher.

## How it works

Place Python scripts (`.py` files) in the `moosescript` folder (located inside the `minecraft`
folder) to run them from the Minecraft chat console. A file at `minecraft/moosescript/example.py`
can be executed from the Minecraft chat as:

```
\example
```

`moosescript.py` is a script library that's automatically installed in the
`minecraft/moosescript/system/lib` folder the first time running Minecraft with the Moosescript mod
installed. `moosescript.py` contains a library of functions for accessing Minecraft functionality:

```
# example.py:

import moosescript

# Write a message to the chat that only you can see:
moosescript.echo("Hello, world!")

# Write a chat message that other players can see:
moosescript.chat("Hello, everyone!")

# Get your player's current position:
x, y, z = moosescript.player().position

# Print information for the block that your player is standing on:
moosescript.echo(moosescript.getblock(x, y - 1, z))

# Set the block directly beneath your player (assuming commands are enabled):
x, y, z = int(x), int(y), int(z)
moosescript.execute(f"setblock {x} {y-1} {z} yellow_concrete")

# Display the contents of your inventory:
for item in moosescript.player_inventory():
  moosescript.echo(item.item)
```

## Pre-built mod jars

There are currently no distributed versions of Moosescript.

## Minescript License

All code, compiled or in source form, in the built mod jar is licensed as GPL
v3 (specifically `SPDX-License-Identifier: GPL-3.0-only`). Sources within the
`tools` directory that are not distributed in the mod jar and are not required
for building or running the mod jar may be covered by a different license.

## Minescript Credits
Special thanks to **Spiderfffun** and **Coolbou0427** for extensive testing on Windows.
