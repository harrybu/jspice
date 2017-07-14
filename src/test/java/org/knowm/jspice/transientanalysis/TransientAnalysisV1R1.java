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
package org.knowm.jspice.transientanalysis;

import org.knowm.jspice.JSpice;
import org.knowm.jspice.circuits.V1R1;
import org.knowm.jspice.netlist.Netlist;
import org.knowm.jspice.simulate.SimulationPlotter;
import org.knowm.jspice.simulate.SimulationResult;
import org.knowm.jspice.simulate.transientanalysis.TransientConfig;
import org.knowm.jspice.simulate.transientanalysis.driver.Driver;
import org.knowm.jspice.simulate.transientanalysis.driver.Sine;

public class TransientAnalysisV1R1 {

  public static void main(String[] args) {

    Netlist netlist = new V1R1();
    netlist.setSimulationConfig(new TransientConfig(2, .01, new Driver[]{new Sine("V1", 2.5, 0, 2.5, 1.0)}));
    SimulationResult simulationResult = JSpice.simulate(netlist);
    SimulationPlotter.plot(simulationResult, "I(R1)");

    //    // run via NetlistBuilder
    //    NetlistBuilder builder = new NetlistBuilder().addNetlistDCVoltage("V1", 1.0, "1", "0").addNetlistResistor("R1", 1000, "1", "0")
    //        .addTransientSimulationConfig(2, .01, new Driver[]{new Sine("V1", 2.5, 0, 2.5, 1.0)});
    //    Netlist netlist = builder.build();
    //    System.out.println("builder.getYAML() " + builder.getYAML());
    //    JSpice.simulate(netlist);
  }
}
