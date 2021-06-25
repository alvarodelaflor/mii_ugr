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
					<section className="py-5">
						<div className="container px-4 px-lg-5 mt-5">
							<div
								className="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
								{items.map(item => (
									<div className="col mb-5">
										<div className="card h-100">
											<img className="card-img-top" src={item.foto}
												 alt={item.foto}/>
											<div className="card-body p-4">
												<div className="text-center">
													<h5 className="fw-bolder">{item.nombre}</h5>
													<div className="d-flex justify-content-center small text-warning mb-2">
														<div className="bi-star-fill"></div>
														<div className="bi-star-fill"></div>
														<div className="bi-star-fill"></div>
														<div className="bi-star-fill"></div>
														<div className="bi-star-fill"></div>
													</div>
													<span className="text-muted">{item.owner}:</span>
													<span>{item.descripcion}</span>
													<br></br>
												</div>
											</div>
										</div>
									</div>
								))}
							</div>
						</div>
					</section>
				</div>
			)
		}
	}
}

export default App;
