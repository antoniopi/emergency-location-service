import React from "react";
import { useState, useEffect } from "react";
import {MapContainer, Marker, Popup, TileLayer} from 'react-leaflet';
import './App.css';
import SockJsClient from 'react-stomp';
import MarkerClusterGroup from "react-leaflet-markercluster/src/react-leaflet-markercluster";

const position = [53.4134654,-6.3655679958999851]

function App() {

    const SOCKET_URL = 'http://localhost:8080/ws-endpoint';
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch("/api/emergencies")
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


     let onMessageReceived = (msg,topic) => {
        if (topic === "/topic/emergencies/new") {
            setItems(oldArray => [...oldArray, msg]);
        } else if (topic === "/topic/emergencies/update") {
            console.log("Updated emergency")
        } else if (topic === "/topic/emergencies/delete/all") {
            setItems([])
        }
      }

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <div>
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/topic/emergencies/new','/topic/emergencies/update','/topic/emergencies/delete','/topic/emergencies/delete/all']}
                    onConnect={console.log("Connected!")}
                    onDisconnect={console.log("Disconnected!")}
                    onMessage={ onMessageReceived }
                    debug={false}
                />
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
            </div>
        );
    }
}

export default App;