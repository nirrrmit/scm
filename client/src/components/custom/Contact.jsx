import React from "react";
import { Card, CardContent } from "@/components/ui/card";

export function Contact({ firstName, lastName, email }) {
  return (
    <Card>
      <CardContent className="p-4">
        <h2 className="text-lg font-semibold">{`${firstName} ${lastName}`}</h2>
        <p className="text-gray-600">{email}</p>
      </CardContent>
    </Card>
  );
}
