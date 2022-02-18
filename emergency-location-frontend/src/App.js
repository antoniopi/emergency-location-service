import React from "react";
import { useState, useEffect } from "react";
import {MapContainer, Marker, Popup, TileLayer} from 'react-leaflet';
import './App.css';
import MarkerClusterGroup from "react-leaflet-markercluster/src/react-leaflet-markercluster";

const position = [53.4134654,-6.3655679958999851]

function App() {

    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch("https://emergency-location-service.herokuapp.com/api/emergencies")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    console.log(result)
                    setItems(result);
                },
                (error) => {
                    setIsLoaded(true);
                    console.log(error)
                    setError(error);
                }
            )
    }, [])

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <MapContainer center={position} zoom={13}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <MarkerClusterGroup>
                    {items.map(item => (
                        <Marker position={[item.location.latitude, item.location.longitude]}>
                            <Popup>
                                Emergency: Call
                                from {item.callerTelephoneNumber} ({item.callerName} {item.callerLastname})
                            </Popup>
                        </Marker>
                    ))}
                </MarkerClusterGroup>
            </MapContainer>
        );
    }
}

export default App;