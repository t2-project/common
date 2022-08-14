package de.unistuttgart.t2.common.scaling;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import de.unistuttgart.t2.common.scaling.cpu.*;
import de.unistuttgart.t2.common.scaling.memory.*;
import de.unistuttgart.t2.common.scaling.request.RequestDenier;
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

    @Operation(summary = "Ensures that consistently at least {memory}% is used", description = "values in range 0 <= {memory} < 1 are treated the same as values in range 1 <= {memory} < 100", tags = "Memory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully demanded a memory usage of at least {memory}%"),
        @ApiResponse(responseCode = "400", description = "{memory} >= 100.0 || {memory} < 0.0") })
    @PostMapping("/autoscaling/require-memory/{memory}")
    public void requireMemory(@PathVariable(name = "memory") double memory) {
        MemoryLeaker.changeExpectedMemoryPercentage(memory);
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

    @Operation(summary = "Ensures that consistently at least {cpu}% is used", description = "time unit = values known to https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/temporal/ChronoUnit.html#valueOf(java.lang.String), case insensitive, default seconds.\n"
        + "CPU percentage defines the percentage of CPU to use at all times, for all cores combined, hence must be lower than 100*{number of cores}.\n"
        + "The mechanism works by using 100% per core for an interval of length {requested CPU percentage} * {interval length} / {number of cores} periodically.\n"
        + "Interval length decides how long the interval is in {time unit}. Default 10.", tags = "CPU")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully demanded a CPU usage of at least {cpu}%") })
    @PostMapping("/autoscaling/require-cpu")
    public void requireCPU(@RequestBody @Valid CPUUsageRequest cpu) {
        CPUUsageManager.requireCPU(cpu.convert());
    }

    @Operation(summary = "Removes the requirement to use a minimum of CPU at all times", tags = "CPU")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully disabled the minimum usage requirements for the CPU"))
    @PostMapping("/autoscaling/remove-cpu-usage-requirements")
    public void removeCPURequirements() {
        CPUUsageManager.stop();
    }

    @Operation(summary = "Show current CPU information", description = "Returns how many cores are vailable, what interval is used and .", tags = "CPU")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully returned the current CPU information"))
    @GetMapping(path = "/autoscaling/cpu-info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CPUUsage getCPUInformation() {
        return CPUUsageManager.getCurrentStatus();
    }
}
