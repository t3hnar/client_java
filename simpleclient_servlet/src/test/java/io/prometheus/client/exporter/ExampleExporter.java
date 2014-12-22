package io.prometheus.client.exporter;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ExampleExporter {

  static final Gauge g = (Gauge) Gauge.build().name("gauge").help("blah").register();
  static final Counter c = (Counter) Counter.build().name("counter").help("meh").register();
  static final Summary s = (Summary) Summary.build().name("summary").help("meh").register();
  static final Gauge l = (Gauge) Gauge.build().name("labels").help("blah").labelNames("l").register();

  public static void main(String[] args) throws Exception {
    Server server = new Server(1234);
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");
    server.setHandler(context);
    context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");
    server.start();
    g.set(1);
    c.inc(2);
    s.observe(3);
    l.labels("foo").inc(4);
    while(true) {
    }
  }

}