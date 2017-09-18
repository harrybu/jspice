package org.knowm.jspice.netlist;

import org.knowm.jspice.component.element.linear.ResistorArbitrary;

public class NetlistResistorArbitrary extends NetlistComponent {

  public NetlistResistorArbitrary(String id, double resistance, String expression, String... nodes) {

    super(new ResistorArbitrary(id, resistance, expression), nodes);
  }
}
