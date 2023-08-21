import { Fornecedor} from "../fornecedor/fornecedor"

export class Pecas {
    pecas_id?: number;
    nome?: string;
    descricao?: string;
    quantidade?: number;
    foto?: Blob; 
    base64?: string;
    marca?: string;
    ano?: number;
    preco?: number;
    modelo?: string;
    
    fornecedor: Fornecedor = new Fornecedor();

}
