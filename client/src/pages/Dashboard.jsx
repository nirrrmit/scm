import { ContactFormModal } from '@/components/custom/ContactFormModal';
import { Contact } from '@/components/custom/Contact';
import { LogoutButton } from '@/components/custom/LogoutButton';
import { AddContactButton } from '@/components/custom/AddContactButton';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
    const [contacts, setContacts] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingContact, setEditingContact] = useState(null)

    const apiUrl = import.meta.env.VITE_API_URL;

    const navigate = useNavigate();

    const fetchContacts = async () => {
        try {
            const response = await fetch(`${apiUrl}/contacts`, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            });

            if (!response.ok) {
                if (response.status == 403) navigate("/"); // using this instead of Protected Routes
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }

            const data = await response.json();
            setContacts(data);
        } catch (error) {
            console.error('Failed to fetch contacts:', error.message);
        }
    };

    useEffect(() => {
        fetchContacts();
    }, []);

    const handleEditClick = (contact) => {
        setEditingContact(contact)
        setIsModalOpen(true)
    }

    const handleAddOrEditContact = async (contactData) => {
        if (editingContact) {
            setContacts(contacts.map(c => c.id === contactData.id ? contactData : c))
        } else {
            setContacts([...contacts, { ...contactData}])
        }
    }

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`${apiUrl}/contacts/${id}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
            });
    
            if (!response.ok) {
                if (response.status === 403) {
                    console.error('Unauthorized access. Redirecting to login.');
                    navigate("/");
                    return;
                }
                throw new Error(`Failed to delete contact. Status: ${response.status}`);
            }
    
            // If the delete was successful, remove the contact from state
            setContacts((prevContacts) => prevContacts.filter((contact) => contact.id !== id));
        } catch (error) {
            console.error('Error deleting contact:', error.message);
        }
    };
    

    return (
        <div className="min-h-screen flex flex-col">
            <header className="bg-black text-white p-4 flex justify-between items-center">
                <h1 className="text-2xl font-bold">Smart Contact Manager</h1>
                <LogoutButton />
            </header>
            <main className="flex-grow p-6 bg-gray-100">
                <div className="space-y-4">
                    {contacts.map((contact) => {
                        return <Contact
                            key={contact.id} {...contact}
                            onEdit={() => handleEditClick(contact)}
                            onDelete={handleDelete} />
                    })}
                </div>
                <AddContactButton setIsModalOpen={setIsModalOpen} />
                <ContactFormModal
                    isOpen={isModalOpen}
                    onClose={() => {
                        setIsModalOpen(false)
                        setEditingContact(null)
                    }}
                    onSubmit={handleAddOrEditContact}
                    existingContact={editingContact}
                />
            </main>
        </div>
    );
}
