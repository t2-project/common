package de.unistuttgart.t2.common.scaling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	RequestDenier	denier;
	@Autowired
	MemoryLeaker	leaker;

	@Operation(summary = "Unblock all requests", description = "Lifts the block for any route", tags = {	"Autoscaling",
																											"Block" })
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully lifted the route block"))
	@PostMapping("/autoscaling/unblock-routes")
	public void unblockRoutes() {
		denier.shouldBlockAllRoutes(false);
	}

	@Operation(summary = "Block all subsequent requests", description = "deterministically produces a SLO for all subsequent requests outside of \"/autoscaling/*\" ", tags = { "Autoscaling",
																																												"Block" })
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully blocked all further routes"))
	@PostMapping("/autoscaling/block-routes")
	public void blockRoutes() {
		denier.shouldBlockAllRoutes(true);
	}

	@Operation(summary = "Ensures that consistently at least {memory}% is used", description = "values in range 0 < {memory} < 1 are treated the same as values in range 1 <= {memory} < 100", tags = { "Autoscaling",
																																																		"Memory" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully demanded a memory usage of at least {memory}%"),
							@ApiResponse(responseCode = "400", description = "{memory} >= 100.0") })
	@PostMapping("/autoscaling/require-memory/{memory}")
	public void requireMemory(@PathVariable(name = "memory") double memory) {
		try {
			leaker.changeExpectedMemoryPercentage(memory);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String
				.format("%f is greater than the allowed maximum value of 100.0", memory));
		}
	}

	@Operation(summary = "Clear the memory leak if it exists", description = "Let's the service return to its normal memory usage.", tags = {	"Autoscaling",
																																				"Memory" })
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully cleared the memory leak"))
	@PostMapping("/autoscaling/clear-memory-leak")
	public void clearMemoryLeak() {
		leaker.clearMemoryLeak();
	}

	@Operation(summary = "Disable adding more unneeded memory", description = "Needed when wanting to keep an already existing memory leak without increasing the leaked amount of memory once the GC frees some other memory.", tags = {	"Autoscaling",
																																																											"Memory" })
	@ApiResponses(value = @ApiResponse(responseCode = "200", description = "Successfully disabled the memory leak"))
	@PostMapping("/autoscaling/disable-memory-leak")
	public void disableMemoryLeak() {
		leaker.changeExpectedMemoryPercentage(0.0);
	}
}
