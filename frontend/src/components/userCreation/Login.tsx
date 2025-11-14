import { cn } from "../../lib/utils";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../ui/card";
import { Button } from "../ui/button";
import { Field, FieldDescription, FieldGroup, FieldLabel } from "../ui/field";
import { Input } from "../ui/input";
import { useState } from "react";

interface loginProps {
  className: string;
  setActiveTab: React.Dispatch<React.SetStateAction<"login" | "signup">>;
}

// main page for logging into

function Login({ className, setActiveTab }: loginProps) {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const loginAccount = () => {
    // Logic for logging in the user goes here
  };

  return (
    <>
      <div className={cn("flex flex-col gap-6", className)}>
        <Card>
          <CardHeader>
            <CardTitle>Login</CardTitle>
            <CardDescription>
              Enter your email below to login to your account!
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form>
              <FieldGroup>
                <Field>
                  <FieldLabel>Email</FieldLabel>
                  <Input
                    id="email"
                    type="email"
                    placeholder="abc@example.com"
                    required
                    onChange={(e) => setUserEmail(e.target.value)}
                  />
                </Field>
                <Field>
                  <FieldLabel>Password</FieldLabel>
                  <Input
                    id="password"
                    type="password"
                    placeholder="password"
                    required
                    onChange={(e) => setUserPassword(e.target.value)}
                  />
                </Field>
                <Field>
                  <Button type="submit">Login</Button>
                  <FieldDescription className="text-center">
                    Don't have an account?{" "}
                    <Button
                      className="ml-4"
                      type="button"
                      variant="outline"
                      onClick={() => setActiveTab("signup")}
                    >
                      Sign up
                    </Button>
                  </FieldDescription>
                </Field>
              </FieldGroup>
            </form>
          </CardContent>
        </Card>
      </div>
    </>
  );
}

export default Login;
