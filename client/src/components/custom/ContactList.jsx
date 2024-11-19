import { Contact } from "./Contact";

export function ContactList({ contacts }) {

    if (!contacts.length) {
        return <div>No contacts found.</div>;
    }

    return (
        <div className="space-y-4">
            {contacts.map((contact) => {
                console.log(contact.id)
                return <Contact key={contact.id} {...contact} />
            })}
        </div>
    );
}
