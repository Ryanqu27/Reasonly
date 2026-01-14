import { useState } from 'react'
import { useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { getAllEngineers, 
  getEngineerById, 
  createEngineer, 
  updateEngineer,
  deleteEngineer
 } from './SoftwareEngineerService.js'



function ChangeButtonColor() {
  const [color, setColor] = useState("blue");
  function switchColor() {
    if (color == "blue") {
      setColor("red");
    }
    else {
      setColor("blue");
    }
  }
  return (
    <div>
      <p>Current color is {color}</p>
      <button onClick={switchColor}>Click me</button>
    </div>
  )
  
}

function Engineers() {
  const [engineers, setEngineers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [randomVariable, setRandomVariable] = useState(true);
  useEffect(() => {
    loadEngineers();
  }, [randomVariable]);

  const loadEngineers = async () => {
    try {
      const res = await getAllEngineers();
      setEngineers(res.data);
    } catch (err) {
      setError("Failed to load engineers");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;
  function changeVariable() {
    if (randomVariable) {
      setRandomVariable(false);
    }
    else {
      setRandomVariable(true);
    }
  }
  return (
    <div>
      <button onClick={changeVariable}>Get Engineers</button>
      <ul>
      {engineers.map(e => (
        <li key={e.id}>
          {e.name} — {e.techStack}
        </li>
      ))}
      </ul>
    </div>
    
  );
}


function App() {

  return (
    <div className="App">
      <ChangeButtonColor />
      <Engineers />
    </div>
    
  )
}


export default App
