import React from "react";
import { Button } from "@/components/ui/button";
import { PlusIcon } from "lucide-react";

export function AddContactButton({ setIsModalOpen }) {

    return (
        <>
            <Button
                onClick={() => setIsModalOpen(true)}
                className="mt-4 w-full h-14 bg-gray-50 text-gray-800 border-2 border-dashed border-gray-300 hover:bg-gray-100 hover:border-gray-400"
            >
                <PlusIcon className="mr-2 h-4 w-4" /> Add Contact
            </Button>
        </>
    );
}
