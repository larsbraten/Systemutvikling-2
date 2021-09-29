import React from "react";
class Camper extends React.Component {
  render() {
    return (
      <tr>
        <td>{this.props.id}</td>
        <td>
          <a href={this.props.link} target="_blank">
            <img src={this.props.image}></img>
          </a>
          <a href={this.props.link} target="_blank">
            {this.props.name}
          </a>
        </td>
        <td className="points">{this.props.recent}</td>
        <td className="points">{this.props.alltime}</td>
      </tr>
    );
  }
}

export default Camper;
