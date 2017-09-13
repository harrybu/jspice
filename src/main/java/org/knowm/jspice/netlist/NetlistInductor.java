package org.knowm.jspice.netlist;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.knowm.jspice.component.element.reactive.Inductor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Harald Bucher
 */
public class NetlistInductor extends NetlistReactiveElement {

  @Valid
  @NotNull
  @JsonProperty("inductance")
  double inductance;

  public NetlistInductor(String id, double inductance, String... nodes) {

    super(new Inductor(id, inductance), nodes);
    this.inductance = inductance;
  }

  @JsonCreator
  public NetlistInductor(@JsonProperty("id") String id, @JsonProperty("inductance") double inductance, @JsonProperty("nodes") String nodes) {

    super(new Inductor(id, inductance), nodes);
    this.inductance = inductance;
  }

  public NetlistInductor(String id, double inductance, double initialCondition, String... nodes) {

    super(new Inductor(id, inductance), initialCondition, nodes);
    this.inductance = inductance;
    this.initialCondition = initialCondition;
  }

  @JsonCreator
  public NetlistInductor(
      @JsonProperty("id") String id,
      @JsonProperty("inductance") double inductance,
      @JsonProperty("initialCondition") double initialCondition,
      @JsonProperty("nodes") String nodes) {

    super(new Inductor(id, inductance), initialCondition, nodes);
    this.inductance = inductance;
    this.initialCondition = initialCondition;

  }

}
