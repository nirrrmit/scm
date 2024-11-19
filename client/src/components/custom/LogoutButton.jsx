import React from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";

export function LogoutButton() {

  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  const handleLogout = async () => {
    try {
        const response = await fetch(`${apiUrl}/logout`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            credentials: "include",
        });

        if (response.ok) {
            navigate("/");
        } else {
            console.error("Logout failed:", response.statusText);
        }
    } catch (error) {
        console.error("An error occurred during logout:", error.message);
    }
};

  return (
    <Button onClick={handleLogout} variant="outline" className="bg-white text-black hover:bg-gray-200">
      Logout
    </Button>
  );
}

