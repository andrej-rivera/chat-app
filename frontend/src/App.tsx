import { Outlet } from "react-router";
import "./App.css";
function App() {
  return (
    <>
      <div className="dark bg-background m-0 p-0 bd-0">
        <Outlet />
      </div>
    </>
  );
}

export default App;
