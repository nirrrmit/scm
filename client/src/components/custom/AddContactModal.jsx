import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox"; // Assuming you have a Checkbox component
import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
	DialogFooter,
} from "@/components/ui/dialog";
import { Textarea } from "../ui/textarea";

export function AddContactModal({ isOpen, onClose, addContact }) {
	
	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [phoneNumber, setPhoneNumber] = useState("");
	const [email, setEmail] = useState("");
	const [about, setAbout] = useState("");
	const [favorite, setFavorite] = useState(false);
	const [error, setError] = useState(null);

	const apiUrl = import.meta.env.VITE_API_URL;

	const handleSubmit = async (e) => {
		e.preventDefault();

		const contactData = {
			firstName,
			lastName,
			phoneNumber,
			email,
			about,
			favorite,
		};

		try {
			const response = await fetch(`${apiUrl}/contacts/add`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(contactData),
				credentials: 'include'
			});

			if (!response.ok) {
                const errorData = await response.json();
                setError(errorData.message);
                return;
            }

			const newContact = await response.json();
			addContact(newContact);

			// Reset form and close modal
			setFirstName("");
			setLastName("");
			setPhoneNumber("");
			setEmail("");
			setAbout("");
			setFavorite(false);
			setError(null);
			onClose();

		} catch (error) {
			setError("An unexpected error occurred: " + error.message);
		}

	};

	return (
		<Dialog open={isOpen} onOpenChange={onClose}>
			<DialogContent>
				<DialogHeader>
					<DialogTitle>Add New Contact</DialogTitle>
				</DialogHeader>
				<form onSubmit={handleSubmit}>
					<div className="space-y-4">
						{/* First Name */}
						<div>
							<Label htmlFor="firstName">First Name</Label>
							<Input
								id="firstName"
								value={firstName}
								onChange={(e) => setFirstName(e.target.value)}
								required
							/>
						</div>

						{/* Last Name */}
						<div>
							<Label htmlFor="lastName">Last Name</Label>
							<Input
								id="lastName"
								value={lastName}
								onChange={(e) => setLastName(e.target.value)}
								required
							/>
						</div>

						{/* Phone Number */}
						<div>
							<Label htmlFor="phoneNumber">Phone Number</Label>
							<Input
								id="phoneNumber"
								value={phoneNumber}
								onChange={(e) => setPhoneNumber(e.target.value)}
							/>
						</div>

						{/* Email */}
						<div>
							<Label htmlFor="email">Email*</Label>
							<Input
								id="email"
								type="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								required
							/>
						</div>

						{/* About */}
						<div>
							<Label htmlFor="about">About</Label>
							<Textarea
								id="about"
								value={about}
								onChange={(e) => setAbout(e.target.value)}
								className="resize-none"
							/>
						</div>

						{/* Favorite */}
						<div className="flex items-center space-x-2">
							<Checkbox
								id="favorite"
								onChange={(e) => setFavorite(e.target.checked)}
							/>
							<Label htmlFor="favorite">Mark as Favorite</Label>
						</div>
					</div>

					<DialogFooter className="mt-6">
						<Button type="button" variant="outline" onClick={onClose}>
							Cancel
						</Button>
						<Button type="submit">Submit</Button>
						{error && <p className="text-red-500 text-sm mt-4">{error}</p>}
					</DialogFooter>
				</form>
			</DialogContent>
		</Dialog>
	);
}
