import { useState } from "react";
import Login from "../components/userCreation/Login";
import Signup from "../components/userCreation/Signup";

function LoginPage() {
  const [activeTab, setActiveTab] = useState<"login" | "signup">("login");

  const displayTab = () => {
    switch (activeTab) {
      case "login":
        return <Login className="" setActiveTab={setActiveTab} />;
      case "signup":
        return <Signup className="" setActiveTab={setActiveTab} />;
    }
  };

  return (
    <>
      <div className="h-screen mx-auto flex justify-center items-center">
        <div className="w-full max-w-sm">{displayTab()}</div>
      </div>
    </>
  );
}

export default LoginPage;
