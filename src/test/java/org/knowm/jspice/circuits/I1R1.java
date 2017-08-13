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
package org.knowm.jspice.circuits;

import org.knowm.jspice.netlist.Netlist;
import org.knowm.jspice.netlist.NetlistDCCurrent;
import org.knowm.jspice.netlist.NetlistResistor;

/**
 * This circuit is simply a dc current source and a single resistor
 *
 * @author timmolter
 */
public class I1R1 extends Netlist {

  /**
   * Constructor
   */
  public I1R1() {

    addNetListComponent(new NetlistDCCurrent("a", 1.0, "0", "1"));
    addNetListComponent(new NetlistResistor("R1", 1000, "1", "0"));
  }
}