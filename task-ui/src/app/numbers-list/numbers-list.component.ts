import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { ExampleDataSource } from '../DataSource';
import { NumberData } from '../Number';
import { NumberApiServiceService } from '../number-api-service.service';
import { NumberResponse } from '../NumberResponse';

@Component({
  selector: 'app-numbers-list',
  templateUrl: './numbers-list.component.html',
  styleUrls: ['./numbers-list.component.css']
})
export class NumbersListComponent implements OnInit {
  @ViewChild(MatPaginator)
  paginator: MatPaginator;
  response: NumberResponse = {};
  displayedColumns: string[] = ['id', 'name', 'phone', 'country', 'state'];
  dataSource?: ExampleDataSource;
  currentPageSize: number = 5;
  totalItems: number = 0;
  currentPageIndex: number = 0;
  sortDirection: string = "ASC";
  sort: string = "none";
  state: string = "none";
  country: string = "none";
  countryList: string[] = [];
  constructor(private numbserService: NumberApiServiceService) {
  }

  ngOnInit(): void {
    this.getCountries();

    this.getNumber();
  }
  getCountries(): void {
    this.numbserService.getSupportedCountries()
      .subscribe(apiResponse => {
        this.countryList = Object.keys(apiResponse);
      });
  }
  getNumber(): void {
    this.numbserService.getAllNumbers(this.currentPageIndex, this.currentPageSize, this.sortDirection, this.sort, this.state, this.country)
      .subscribe(apiResponse => {
        this.response = apiResponse;
        this.dataSource = new ExampleDataSource(apiResponse.data!);
        this.totalItems = apiResponse.total!;
      });
  }
  onPaginateChange(event: any) {
    this.currentPageIndex = event.pageIndex;
    this.currentPageSize = event.pageSize;
    this.getNumber();
  }
  onSortDirectionChanges(event: any) {
    this.sortDirection = event.value;
    this.resetToZeroPage();
    this.getNumber();
  }
  onSorChanges(event: any) {
    this.sort = event.value;
    this.resetToZeroPage();
    this.getNumber();
  }

  onValidFilterChange(event: any) {
    this.state = event.value;
    this.resetToZeroPage();
    this.getNumber();
  }
  onCountryChange(event: any) {
    this.country = event.value;
    this.resetToZeroPage();
    this.getNumber();
  }
  resetToZeroPage() {
    this.paginator.pageIndex = 0;
    this.currentPageIndex = 0;
  }
}
