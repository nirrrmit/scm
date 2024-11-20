import React from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { StarIcon, PhoneIcon, MailIcon, InfoIcon, EditIcon, TrashIcon } from 'lucide-react'

export function Contact({ id, firstName, lastName, phoneNumber, email, about, favorite, onEdit, onDelete }) {
	return (
		<Card>
			<CardContent className="p-4">
				<div className="flex justify-between items-start mb-2">
					<h2 className="text-lg font-semibold">{`${firstName} ${lastName}`}</h2>
					{favorite && (
						<Badge variant="secondary">
							<StarIcon className="h-4 w-4 mr-1" />
							Favorite
						</Badge>
					)}
				</div>


				<div className="flex justify-between items-start mb-2">
					<div className="space-y-2">
						{phoneNumber && (
							<div className="flex items-center text-sm text-gray-600">
								<PhoneIcon className="h-4 w-4 mr-2" />
								<span>{phoneNumber}</span>
							</div>
						)}
						<div className="flex items-center text-sm text-gray-600">
							<MailIcon className="h-4 w-4 mr-2" />
							<span>{email}</span>
						</div>
						{about && (
							<div className="flex items-start text-sm text-gray-600">
								<InfoIcon className="h-4 w-4 mr-2 mt-1" />
								<p className="flex-1">{about}</p>
							</div>
						)}
					</div>
					<div className="flex justify-end space-x-2 self-end">
						<Button variant="outline" size="sm">
							<EditIcon className="h-4 w-4" onClick={onEdit} />
						</Button>
						<Button variant="outline" size="sm">
							<TrashIcon className="h-4 w-4" onClick={() => onDelete(id)} />
						</Button>
					</div>
				</div>



			</CardContent>
		</Card>
	);
}
