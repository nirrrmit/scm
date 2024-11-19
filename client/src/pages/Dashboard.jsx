import { AddContactModal } from '@/components/custom/AddContactModal';
import { ContactList } from '@/components/custom/ContactList';
import { LogoutButton } from '@/components/custom/LogoutButton';
import { AddContactButton } from '@/components/custom/AddContactButton';
import { Suspense, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
    const [contacts, setContacts] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

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
                if(response.status == 403) navigate("/"); // using this instead of Protected Routes
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }

            const data = await response.json();
            setContacts(data);
        } catch (error) {
            console.error('Failed to fetch contacts:', error.message);
        }
    };

    useState(() => {
        fetchContacts();
    }, []);

    const addContact = (newContact) => {
        setContacts((prevContacts) => [...prevContacts, newContact]);
    };

    return (
        <div className="min-h-screen flex flex-col">
            <header className="bg-black text-white p-4 flex justify-between items-center">
                <h1 className="text-2xl font-bold">Dashboard</h1>
                <LogoutButton />
            </header>
            <main className="flex-grow p-6 bg-gray-100">
                <Suspense fallback={<div>Loading contacts...</div>}>
                    <ContactList contacts={contacts} />
                </Suspense>
                <AddContactButton setIsModalOpen={setIsModalOpen} />
                <AddContactModal
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                    addContact={addContact}
                />
            </main>
        </div>
    );
}
