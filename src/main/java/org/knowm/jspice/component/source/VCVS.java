/**
 * jspice is distributed under the GNU General Public License version 3
 * and is also available under alternative licenses negotiated directly
 * with Knowm, Inc.
 *
 * Copyright (c) 2016-2017 Knowm Inc. www.knowm.org
 *
 * Knowm, Inc. holds copyright
 * and/or sufficient licenses to all components of the jspice
 * package, and therefore can grant, at its sole discretion, the ability
 * for companies, individuals, or organizations to create proprietary or
 * open source (even if not GPL) modules which may be dynamically linked at
 * runtime with the portions of jspice which fall under our
 * copyright/license umbrella, or are distributed under more flexible
 * licenses than GPL.
 *
 * The 'Knowm' name and logos are trademarks owned by Knowm, Inc.
 *
 * If you have any questions regarding our licensing policy, please
 * contact us at `contact@knowm.org`.
 */
package org.knowm.jspice.component.source;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.knowm.jspice.netlist.Netlist;
import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;

/**
 * @author timmolter
 */
public class VCVS extends Source {
    
  public static final String SPICE_PREFIX_ID = "E";


  private double gain; // a.k.a. `k`

  /**
   * Constructor
   *
   * @param id
   * @param transconductance
   */
  public VCVS(String id, double transconductance) {

    super(_prependSpicePrefixID(id,
              SPICE_PREFIX_ID));
    this.gain = transconductance;
  }

  @Override
  public void setSweepValue(double value) {

    this.gain = value;
  }

  @Override
  public double getSweepableValue() {

    return gain;
  }

  @Override
  public String toString() {

    return "VCVS [id=" + getId() + ", gain=" + gain + "]";
  }

  @Override
  public Set<String> getGMatrixColumnIDs(String[] nodes, Double timeStep) {

    Set<String> set = new HashSet<>(3);
    set.add(getId());
    set.add(nodes[0]);
    set.add(nodes[1]);

    return set;
  }

  @Override
  public void modifyUnknowmQuantitiesVector(String[] columnQuantities, String[] nodes, Double timeStep) {

    for (int i = 0; i < columnQuantities.length; i++) {
      if (columnQuantities[i].equals(nodes[0]) || columnQuantities[i].equals(nodes[1])) {
        columnQuantities[i] = "V(" + columnQuantities[i] + ")";
      } else if (columnQuantities[i].equals(getId())) {
        columnQuantities[i] = "I(" + columnQuantities[i] + ")";
      }
    }
  }

  @Override
  public void stampG(double[][] G, Netlist netList, DCOperatingPointResult dcOperatingPointResult, Map<String, Integer> nodeID2ColumnIdxMap, String[] nodes, Double timeStep) {

    int idxA = nodeID2ColumnIdxMap.get(nodes[0]);
    int idxB = nodeID2ColumnIdxMap.get(nodes[1]);
    int idxAControl = nodeID2ColumnIdxMap.get(nodes[2]);
    int idxBControl = nodeID2ColumnIdxMap.get(nodes[3]);
    int idxI = nodeID2ColumnIdxMap.get(getId());

    // create stamp
    double[][] stamp = new double[5][5];

    stamp[0][0] = 0.0;
    stamp[0][1] = 0.0;
    stamp[0][2] = 0.0;
    stamp[0][3] = 0.0;
    stamp[0][4] = 1.0;
    stamp[1][0] = 0.0;
    stamp[1][1] = 0.0;
    stamp[1][2] = 0.0;
    stamp[1][3] = 0.0;
    stamp[1][4] = -1.0;
    stamp[2][0] = 0.0;
    stamp[2][1] = 0.0;
    stamp[2][2] = 0.0;
    stamp[2][3] = 0.0;
    stamp[2][4] = 0.0;
    stamp[3][0] = 0.0;
    stamp[3][1] = 0.0;
    stamp[3][2] = 0.0;
    stamp[3][3] = 0.0;
    stamp[3][4] = 0.0;
    stamp[4][0] = 1.0;
    stamp[4][1] = -1.0;
    stamp[4][2] = -1 * gain;
    stamp[4][3] = gain;
    stamp[4][4] = 0.0;

    // apply stamp
    G[idxA][idxA] += stamp[0][0];
    G[idxA][idxB] += stamp[0][1];
    G[idxA][idxAControl] += stamp[0][2];
    G[idxA][idxBControl] += stamp[0][3];
    G[idxA][idxI] += stamp[0][4];
    G[idxB][idxA] += stamp[1][0];
    G[idxB][idxB] += stamp[1][1];
    G[idxB][idxAControl] += stamp[1][2];
    G[idxB][idxBControl] += stamp[1][3];
    G[idxB][idxI] += stamp[1][4];
    G[idxAControl][idxA] += stamp[2][0];
    G[idxAControl][idxB] += stamp[2][1];
    G[idxAControl][idxAControl] += stamp[2][2];
    G[idxAControl][idxBControl] += stamp[2][3];
    G[idxAControl][idxI] += stamp[2][4];
    G[idxBControl][idxA] += stamp[3][0];
    G[idxBControl][idxB] += stamp[3][1];
    G[idxBControl][idxAControl] += stamp[3][2];
    G[idxBControl][idxBControl] += stamp[3][3];
    G[idxBControl][idxI] += stamp[3][4];
    G[idxI][idxA] += stamp[4][0];
    G[idxI][idxB] += stamp[4][1];
    G[idxI][idxAControl] += stamp[4][2];
    G[idxI][idxBControl] += stamp[4][3];
    G[idxI][idxI] += stamp[4][4];
  }

  @Override
  public void stampRHS(double[] RHS, DCOperatingPointResult dcOperatingPointResult, Map<String, Integer> nodeID2ColumnIdxMap, String[] nodes, Double timeStep) {

    // Do nothing
  }
}
