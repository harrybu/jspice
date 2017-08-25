/**
 * 
 */
package org.knowm.jspice.component.element.linear;

import java.util.Map;

import org.knowm.jspice.component.NonlinearComponent;
import org.knowm.jspice.netlist.Netlist;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;

/**
 * @author Harald Bucher
 *
 */
public abstract class ResistorArbitrary extends Resistor implements NonlinearComponent {

  public abstract double getArbitraryResistance(DCOperatingPointResult dcOperatingPointResult);


  /**
   * @param id
   * @param resistance
   */
  public ResistorArbitrary(String id, double resistance) {
    super(id, resistance);
  }

  @Override
  public String toString() {

    return "ResisotorArbitrary [id=" + getId() + "]";
  }

  @Override
  public void stampG(double[][] G,
                     Netlist netList,
                     DCOperatingPointResult dcOperatingPointResult,
                     Map<String, Integer> nodeID2ColumnIdxMap,
                     String[] nodes,
                     Double timeStep) {

    int idxA = nodeID2ColumnIdxMap.get(nodes[0]);
    int idxB = nodeID2ColumnIdxMap.get(nodes[1]);

    double[][] stamp = new double[2][2];

    double resistance = getSweepableValue();
    if (dcOperatingPointResult != null) {
      resistance = getArbitraryResistance(dcOperatingPointResult);
      setSweepValue(resistance);
    }
    double conductance = 1 / getSweepableValue();

    // create stamp
    stamp[0][0] = conductance;
    stamp[0][1] = -1 * conductance;
    stamp[1][0] = -1 * conductance;
    stamp[1][1] = conductance;

    // apply stamp
    G[idxA][idxA] += stamp[0][0];
    G[idxA][idxB] += stamp[0][1];
    G[idxB][idxA] += stamp[1][0];
    G[idxB][idxB] += stamp[1][1];

  }

}
