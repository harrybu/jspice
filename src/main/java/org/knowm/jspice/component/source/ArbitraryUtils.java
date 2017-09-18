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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.knowm.jspice.simulate.dcoperatingpoint.DCOperatingPointResult;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * @author timmolter,
 *         Harald Bucher (KIT)
 */
public class ArbitraryUtils {

    public static double getArbitraryValue(DCOperatingPointResult dcOperatingPointResult,
                                           String strExpression) {

      Map<String, Double> symbol2ValueMap = new HashMap<>();
      // Leave out I and V as symbols because they are keywords for SPICE values
      String[] symbolicReplacements = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                                                    "W", "X", "Y", "Z"};

      strExpression.replaceAll("\\s",
                               "");
      // Check for possible "=" in the expression from the form e.g. R=<expression>
      String[] tokens = strExpression.split("=");
      if (tokens.length == 2) {
        strExpression = tokens[1];
      } else if (tokens.length > 2) {
        throw new IllegalArgumentException("ERROR expression syntax error: Only one \"=\" character is allowed! But was \"" + strExpression + "\"");
      }
      String exp4jExpression = strExpression;
      String subFunction;
      int count = 0;
      while ((subFunction =

      getNextNodeOrDeviceValue(exp4jExpression)) != null)

      {
        exp4jExpression = exp4jExpression.replaceFirst(Pattern.quote(subFunction), symbolicReplacements[count]);
        symbol2ValueMap.put(symbolicReplacements[count], dcOperatingPointResult.getValue(subFunction));
        count++;
      }

      ExpressionBuilder expressionBuilder = new ExpressionBuilder(exp4jExpression);
      for (Map.Entry<String, Double> stringDoubleEntry : symbol2ValueMap.entrySet()) {
        expressionBuilder.variable(stringDoubleEntry.getKey());
      }

      Expression expression = expressionBuilder.build();

      for (Map.Entry<String, Double> stringDoubleEntry : symbol2ValueMap.entrySet()) {
        expression.setVariable(stringDoubleEntry.getKey(), stringDoubleEntry.getValue());
      }

      return expression.evaluate();
    }

    /**
     * Helper function to replace Node Voltages or Device Currents with symbols
     * 
     * @param remaining
     * @return The substring to get the DCOP value for (V(x) or I(x))
     */
    private static String getNextNodeOrDeviceValue(String remaining) {
      if (remaining.indexOf("V(") < 0 && remaining.indexOf("I(") < 0) {
        return null;
      }
      if (remaining.indexOf("V(") >= 0) {
        String next = remaining.substring(remaining.indexOf("V("));
        next = next.substring(0,
                              next.indexOf(")") + 1);
        return next;
      }
      if (remaining.indexOf("I(") >= 0) {
        String next = remaining.substring(remaining.indexOf("I("));
        next = next.substring(0,
                              next.indexOf(")") + 1);
        return next;
      }
      return null;
    }
  }
