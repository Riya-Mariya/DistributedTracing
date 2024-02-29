import com.example.DistributedTracing;
import com.example.Edge;
import com.example.TracingService;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistributedTracingTest {
    List<Edge> edges;

    public DistributedTracingTest() throws FileNotFoundException {
        edges = DistributedTracing.readEdgesFromFile("src/test/resources/input.txt");
    }

    @Test
    void testAverageLatencyOfTrace() {
        assertEquals("9", TracingService.averageLatencyOfTrace(edges, "ABC"));
        assertEquals("5", TracingService.averageLatencyOfTrace(edges, "AD"));
        assertEquals("13", TracingService.averageLatencyOfTrace(edges, "ADC"));
        assertEquals("22", TracingService.averageLatencyOfTrace(edges, "AEBCD"));
        assertEquals("NO SUCH TRACE", TracingService.averageLatencyOfTrace(edges, "AED"));
    }

    @Test
    void testNumTracesWithMaxHops() {
        assertEquals(2, TracingService.numTracesWithMaxHops(edges, 'C', 'C', 3));
    }

    @Test
    void testNumTracesWithExactHops() {
        assertEquals(3, TracingService.numTracesWithExactHops(edges, 'A', 'C', 4));
    }

    @Test
    void testShortestTraceLatency() {
        assertEquals(9, TracingService.shortestTraceLatency(edges, 'A', 'C'));
        assertEquals(9, TracingService.shortestTraceLatency(edges, 'B', 'B'));
    }

    @Test
    void testNumTracesWithAvgLatencyLessThan() {
        assertEquals(7, TracingService.numTracesWithAvgLatencyLessThan(edges, 'C', 'C', 30));
    }
}
