import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterCrudSharedModule } from 'app/shared/shared.module';
import { JhipsterCrudCoreModule } from 'app/core/core.module';
import { JhipsterCrudAppRoutingModule } from './app-routing.module';
import { JhipsterCrudHomeModule } from './home/home.module';
import { JhipsterCrudEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhipsterCrudSharedModule,
    JhipsterCrudCoreModule,
    JhipsterCrudHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterCrudEntityModule,
    JhipsterCrudAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JhipsterCrudAppModule {}
