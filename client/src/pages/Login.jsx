import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

import { useNavigate } from "react-router-dom";

export default function Login() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [error, setError] = useState(null)

    const handleEmailChange = (e) => setEmail(e.target.value)
    const handlePasswordChange = (e) => setPassword(e.target.value)
    const handleConfirmPasswordChange = (e) => setConfirmPassword(e.target.value)

    const navigate = useNavigate();

    const apiUrl = import.meta.env.VITE_API_URL;

    const handleSignUp = async (e) => {
        e.preventDefault();
        setError(null);

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        const signUpRequest = {
            email,
            password,
            confirmPassword,
        };

        try {
            const response = await fetch(`${apiUrl}/signup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(signUpRequest),
            });

            if (!response.ok) {
                const errorData = await response.json();
                setError(errorData.message);
                return;
            }

            navigate('/test');

        } catch (error) {
            setError("An unexpected error occurred: " + error.message);
        }
    };


    const handleSignIn = async (e) => {
        e.preventDefault();
        setError(null);

        const signUpRequest = {
            email,
            password
        };

        try {
            const response = await fetch(`${apiUrl}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(signUpRequest),
                credentials: 'include'
            });

            if (!response.ok) {
                const errorData = await response.json();
                setError(errorData.message);
                return;
            }

            navigate('/test');
            
        } catch (error) {
            setError("An unexpected error occurred: " + error.message);
        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen">
            <Card className="w-[400px] shadow-lg">
                <CardHeader className="space-y-1">
                    <CardTitle className="text-3xl font-bold text-center">Contactzz</CardTitle>
                    <CardDescription className="text-center">Your smart contact manager</CardDescription>
                </CardHeader>
                <CardContent>
                    <Tabs defaultValue="signin" className="w-full">
                        <TabsList className="grid w-full grid-cols-2 gap-x-1.5">
                            <TabsTrigger value="signin">Sign In</TabsTrigger>
                            <TabsTrigger value="signup">Sign Up</TabsTrigger>
                        </TabsList>
                        <TabsContent value="signin">
                            <form onSubmit={handleSignIn} className="space-y-4">
                                <div className="space-y-2">
                                    <Label htmlFor="signin-email">Email</Label>
                                    <Input id="signin-email" type="email" placeholder="email@example.com" value={email} onChange={handleEmailChange} required />
                                </div>
                                <div className="space-y-2">
                                    <Label htmlFor="signin-password">Password</Label>
                                    <Input id="signin-password" type="password" value={password} onChange={handlePasswordChange} required />
                                </div>
                                <Button type="submit" className="w-full hover:outline-gray-100">Sign In</Button>
                            </form>
                        </TabsContent>
                        <TabsContent value="signup">
                            <form onSubmit={handleSignUp} className="space-y-4">
                                <div className="space-y-2">
                                    <Label htmlFor="signup-email">Email</Label>
                                    <Input id="signup-email" type="email" placeholder="email@example.com" value={email} onChange={handleEmailChange} required />
                                </div>
                                <div className="space-y-2">
                                    <Label htmlFor="signup-password">Password</Label>
                                    <Input id="signup-password" type="password" value={password} onChange={handlePasswordChange} required />
                                </div>
                                <div className="space-y-2">
                                    <Label htmlFor="signup-confirm-password">Confirm Password</Label>
                                    <Input id="signup-confirm-password" type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} required />
                                </div>
                                <Button type="submit" className="w-full">Sign Up</Button>
                            </form>
                        </TabsContent>
                    </Tabs>
                </CardContent>
                <CardFooter className="flex flex-col">
                    {error && <p className="text-red-500 text-sm mt-4">{error}</p>}
                </CardFooter>
            </Card>
        </div>
    )
}