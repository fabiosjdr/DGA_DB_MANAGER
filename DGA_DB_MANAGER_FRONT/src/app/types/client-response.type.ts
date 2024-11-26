export type Client = {
    id:string,
    responsable:string,
    telephone: string,
    name:string
    hours:string
}

export type ClientResponse = {
    data: Client[]; 
}


