/**
 * 
 */
package org.knowm.jspice.circuit.subcircuit;

import java.util.UUID;

import org.knowm.jspice.circuit.SubCircuit;
import org.knowm.jspice.netlist.NetlistCapacitor;
import org.knowm.jspice.netlist.NetlistDCVoltage;
import org.knowm.jspice.netlist.NetlistResistor;
import org.knowm.jspice.netlist.NetlistResistorArbitrary;

/**
 * SubCircuit representing an arbitrary current load by a variable resistor
 * <p>
 * terminals are:
 * </p>
 * <ul>
 * <li>in</li>
 * <li>out</li>
 * </ul>
 * 
 * @author Harald Bucher
 */
public class ArbitraryCurrentLoad extends SubCircuit {


  private final String _circuitId;

  private final String _in;

  private final String _out;

  /**
   * Constructor
   * 
   * @param in
   * @param out
   * @param circuitId
   */
  public ArbitraryCurrentLoad(String in, String out) {
    this(in, out, UUID.randomUUID().toString());
  }

  public ArbitraryCurrentLoad(String in, String out, String circuitId) {
    _circuitId = circuitId;
    _in = in;
    _out = out;

    String currentNode = "Vint_" + _circuitId;
    String filteredCurrentNode = "Vint_filtered_" + _circuitId;
    final String RESISTOR_CALC_EXPR = "R=(V(" + _in + ")-V(" + _out + "))/((V(" + filteredCurrentNode + ")-V(" + _out + "))+1e-9)";

    // Variable Resistor
    addNetListComponent(new NetlistResistorArbitrary("R1_" + _circuitId, 0.1, RESISTOR_CALC_EXPR, in, out));

    // DC Voltage source specifying the current
    addNetListComponent(new NetlistDCVoltage("V_" + _circuitId, 1.0, currentNode, out));

    // RC Resistor 
    addNetListComponent(new NetlistResistor("R2_" + _circuitId, 1000, currentNode, filteredCurrentNode));
    // RC Capacitor
    addNetListComponent(new NetlistCapacitor("C1_" + _circuitId, 10e-9, filteredCurrentNode, out));

  }


  public String getCircuitId() {
    return _circuitId;
  }

  public String getInNodeId() {
    return _in;
  }

  public String getOutNodeId() {
    return _out;
  }

}
