package org.knowm.jspice.netlist;

import javax.validation.Valid;

import org.knowm.jspice.component.element.reactive.ReactiveElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class NetlistReactiveElement extends NetlistComponent {

  @Valid
  @JsonProperty("initialCondition")
  Double initialCondition = null;


  public NetlistReactiveElement(ReactiveElement component, String... nodesAsArray) {
    super(component, nodesAsArray);
  }

  public NetlistReactiveElement(ReactiveElement component, String nodes) {
    super(component, nodes);
  }

  public NetlistReactiveElement(ReactiveElement component, double initialCondition, String... nodesAsArray) {
    super(component, nodesAsArray);
    component.setInitialCondition(initialCondition);
  }

  public NetlistReactiveElement(ReactiveElement component, double initialCondition, String nodes) {
    super(component, nodes);
    component.setInitialCondition(initialCondition);
  }

}
