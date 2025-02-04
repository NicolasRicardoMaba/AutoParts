import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'navbar-funcionario',
  templateUrl: './navbar-funcionario.component.html',
  styleUrls: ['./navbar-funcionario.component.scss']
})
export class NavbarFuncionarioComponent {

  constructor(private router: Router) {
  }

  linkPainel():void {
    this.router.navigate(['/funcionario/painel']);
  }

  linkListaFuncionario():void {
    this.router.navigate(['/funcionario/lista-funcionario']);
  }

  linkListaProduto():void {
    this.router.navigate(['/funcionario/lista-pecas']);
  }

  linkListaCliente():void {
    this.router.navigate(['/funcionario/lista-cliente']);
  }

  linkListaFornecedor():void {
    this.router.navigate(['/funcionario/lista-fornecedor']);
  }
}
