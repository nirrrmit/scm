import React, { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Checkbox } from "@/components/ui/checkbox"
import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
	DialogFooter,
	DialogDescription,
} from "@/components/ui/dialog"
import { Textarea } from "@/components/ui/textarea"

export function ContactFormModal({ isOpen, onClose, onSubmit, existingContact }) {
	const [firstName, setFirstName] = useState("")
	const [lastName, setLastName] = useState("")
	const [phoneNumber, setPhoneNumber] = useState("")
	const [email, setEmail] = useState("")
	const [about, setAbout] = useState("")
	const [favorite, setFavorite] = useState(false)
	const [error, setError] = useState(null)

	const apiUrl = import.meta.env.VITE_API_URL;

	useEffect(() => {
		if (existingContact) {
			setFirstName(existingContact.firstName)
			setLastName(existingContact.lastName)
			setPhoneNumber(existingContact.phoneNumber)
			setEmail(existingContact.email)
			setAbout(existingContact.about)
			setFavorite(existingContact.favorite)
		} else {
			// Reset form when adding a new contact
			resetForm();
		}
	}, [existingContact])

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
			const response = await fetch(
				existingContact ? `${apiUrl}/contacts/${existingContact.id}` : `${apiUrl}/contacts/add`,
				{
					method: existingContact ? 'PUT' : 'POST', // Use PUT for editing
					headers: {
						'Content-Type': 'application/json',
					},
					body: JSON.stringify(contactData),
					credentials: 'include',
				}
			);
	
			if (!response.ok) {
				const errorData = await response.json();
				setError(errorData.message || "An unexpected error occurred");
				return;
			}
	
			const updatedContact = await response.json();
			onSubmit(updatedContact);
	
			// Reset form and close modal
			resetForm();
			onClose();
	
		} catch (error) {
			setError("An unexpected error occurred: " + error.message);
		}
	};
	
	const resetForm = () => {
		setFirstName("");
		setLastName("");
		setPhoneNumber("");
		setEmail("");
		setAbout("");
		setFavorite(false);
		setError(null);
	};
	

	return (
		<Dialog open={isOpen} onOpenChange={onClose}>
			<DialogContent>
				<DialogHeader>
					<DialogTitle>{existingContact ? "Edit Contact" : "Add New Contact"}</DialogTitle>
					<DialogDescription>{existingContact ? "Make changes to your contact here. Click update when you're done." : "Add contact data here. Click submit when you're done."}</DialogDescription>
				</DialogHeader>
				<form onSubmit={handleSubmit}>
					<div className="space-y-4">
						<div>
							<Label htmlFor="firstName">First Name</Label>
							<Input
								id="firstName"
								value={firstName}
								onChange={(e) => setFirstName(e.target.value)}
								required
							/>
						</div>
						<div>
							<Label htmlFor="lastName">Last Name</Label>
							<Input
								id="lastName"
								value={lastName}
								onChange={(e) => setLastName(e.target.value)}
								required
							/>
						</div>
						<div>
							<Label htmlFor="phoneNumber">Phone Number</Label>
							<Input
								id="phoneNumber"
								value={phoneNumber}
								onChange={(e) => setPhoneNumber(e.target.value)}
							/>
						</div>
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
						<div>
							<Label htmlFor="about">About</Label>
							<Textarea
								id="about"
								value={about}
								onChange={(e) => setAbout(e.target.value)}
								className="resize-none"
							/>
						</div>
						<div className="flex items-center space-x-2">
							<Checkbox
								id="favorite"
								checked={favorite}
								onCheckedChange={(checked) => setFavorite(checked)}
							/>
							<Label htmlFor="favorite">Mark as Favorite</Label>
						</div>
					</div>
					<DialogFooter className="mt-6">
						<Button type="button" variant="outline" onClick={onClose}>
							Cancel
						</Button>
						<Button type="submit">{existingContact ? "Update" : "Submit"}</Button>
					</DialogFooter>
				</form>
				{error && <p className="text-red-500 text-sm mt-2">{error}</p>}
			</DialogContent>
		</Dialog>
	)
}
