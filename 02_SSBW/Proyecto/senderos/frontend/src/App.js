import React, { Component } from 'react';
import { Form} from "react-bootstrap";

class App extends Component {

	constructor(props) {
		super(props)
		this.state = {
			items: [],
			isLoaded: false,
		}
	}
	
	componentDidMount() {
		const url = 'http://localhost/api/excursiones/'

		// una promise, que devuelve otra promise
		console.log("Alvaro0")
		fetch(url,{mode: 'cors'})
			.then(res => res.json())
			.then(json => {
				console.log("Alvaro")
				console.log(json)
				this.setState({
					isLoaded: true,
					items: json,
				})
			})
			.catch(error => {
				alert('Error '+ error)
			})
	}

	render() {

		var {isLoaded, items} = this.state;

		if (!isLoaded) {
			return <div>Loading ...</div>
		} else {
			return (
				<div className="App">
					<ul>
						{items.map(item => (
							<li key={item.nombre}>
								{item.nombre}
							</li>
						))}
					</ul>
				</div>
			)
		}
	}
}

export default App;
