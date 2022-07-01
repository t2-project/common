package de.unistuttgart.t2.common.scaling;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;

/**
 * Contains the routes that (deterministically) influence the scaling behavior.
 *
 * @author Leon Hofmeister
 */
@RestController
public class AutoscalingController {

    @Operation(summary = "Unblock all requests", description = "Lifts the block for any route", tags = "Block")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully lifted the route block"))
    @PostMapping("/autoscaling/unblock-routes")
    public void unblockRoutes() {
        RequestDenier.shouldBlockAllRoutes(false);
    }

    @Operation(summary = "Block all subsequent requests", description = "deterministically produces a SLO for all subsequent requests outside of \"/autoscaling/*\" ", tags = "Block")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully blocked all further routes"))
    @PostMapping("/autoscaling/block-routes")
    public void blockRoutes() {
        RequestDenier.shouldBlockAllRoutes(true);
    }

    @Operation(summary = "Ensures that consistently at least {memory}% is used", description = "values in range 0 < {memory} < 1 are treated the same as values in range 1 <= {memory} < 100", tags = "Memory")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully demanded a memory usage of at least {memory}%"),
                            @ApiResponse(responseCode = "400", description = "{memory} >= 100.0") })
    @PostMapping("/autoscaling/require-memory/{memory}")
    public void requireMemory(@PathVariable(name = "memory") double memory) {
        try {
            MemoryLeaker.changeExpectedMemoryPercentage(memory);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String
                .format("%f is greater than the allowed maximum value of 100.0", memory));
        }
    }

    @Operation(summary = "Clear the memory leak if it exists", description = "Let's the service return to its normal memory usage.", tags = "Memory")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully cleared the memory leak"))
    @PostMapping("/autoscaling/clear-memory-leak")
    public void clearMemoryLeak() {
        MemoryLeaker.clearMemoryLeak();
    }

    @Operation(summary = "Disable adding more unneeded memory", description = "Needed when wanting to keep an already existing memory leak without increasing the leaked amount of memory once the GC frees some other memory.", tags = "Memory")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully disabled the memory leak"))
    @PostMapping("/autoscaling/disable-memory-leak")
    public void disableMemoryLeak() {
        MemoryLeaker.changeExpectedMemoryPercentage(0.0);
    }

    @Operation(summary = "Show current memory information", description = "Returns how many bytes are currently used, free, and in total vailable.", tags = "Memory")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully returned the current memory information"))
    @GetMapping(path = "/autoscaling/memory-info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MemoryInfo getMemoryInformation() {
        return new MemoryInfo();
    }
}
