# common

This repository contains classes that are used by more than one service.
It is a dependency to all other services.

## Structure

* [de.unistuttgart.t2.common](src/main/java/de/unistuttgart/t2/common) :
    Classes used in the various HTTP request that happen around the UI Backend.
* [de.unistuttgart.t2.common.saga](src/main/java/de/unistuttgart/t2/common/saga) :
    The data about the saga instance that the orchestrator passes to the saga participants and vice versa.
* [de.unistuttgart.t2.common.saga.commands](src/main/java/de/unistuttgart/t2/common/saga/commands):
    The commands that the orchestrator passes to the saga participants.
