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

interface loginProps {
  className: string;
  setActiveTab: React.Dispatch<React.SetStateAction<"login" | "signup">>;
}

function Signup({ className, setActiveTab }: loginProps) {
  return (
    <>
      <div className={cn("flex flex-col gap-6", className)}>
        <Card>
          <CardHeader>
            <CardTitle>Sign Up</CardTitle>
            <CardDescription>
              Create an account by filling out the fields.
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
                  />
                </Field>
                <Field>
                  <FieldLabel>Username</FieldLabel>
                  <Input
                    id="username"
                    type="username"
                    placeholder="abracadabra"
                    required
                  />
                </Field>
                <Field>
                  <FieldLabel>Password</FieldLabel>
                  <Input
                    id="password"
                    type="password"
                    placeholder="password"
                    required
                  />
                </Field>
                <Field>
                  <FieldLabel>Confirm Password</FieldLabel>
                  <Input
                    id="password-confirm"
                    type="password-confirm"
                    placeholder="password"
                    required
                  />
                </Field>
                <Field>
                  <Button type="submit">Login</Button>
                  <FieldDescription className="text-center">
                    Already have an account?
                    <Button
                      className="ml-4"
                      type="button"
                      variant="outline"
                      onClick={() => setActiveTab("login")}
                    >
                      Log-In
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

export default Signup;
