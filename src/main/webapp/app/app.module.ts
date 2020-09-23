import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { StripeSharedModule } from 'app/shared/shared.module';
import { StripeCoreModule } from 'app/core/core.module';
import { StripeAppRoutingModule } from './app-routing.module';
import { StripeHomeModule } from './home/home.module';
import { StripeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    StripeSharedModule,
    StripeCoreModule,
    StripeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    StripeEntityModule,
    StripeAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class StripeAppModule {}
