import { Component, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';

import { Link } from './models/Link';
import { LinkService } from './services/link.service';

@Component({
  selector: 'app-root',
  imports: [
    CommonModule,
    ReactiveFormsModule,

    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatDividerModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {

  private readonly linkService = inject(LinkService);
  private readonly snackBar = inject(MatSnackBar);

  links: Link[] = [];

  displayedColumns = [
    'originalUrl',
    'shortUrl',
    'clickCount',
    'createdAt',
    'actions'
  ];

  urlControl = new FormControl('', {
    nonNullable: true,
    validators: [Validators.required]
  });

  loading = false;
  creating = false;

  ngOnInit(): void {
    this.loadLinks();
  }

  loadLinks(): void {
    this.loading = true;

    this.linkService.getAll().subscribe({
      next: (links) => {
        this.links = links;
        this.loading = false;
      },
      error: () => {
        this.loading = false;

        this.snackBar.open(
          'Could not load links.',
          'Close',
          { duration: 3000 }
        );
      }
    });
  }

  createLink(): void {
    if (this.urlControl.invalid) {
      this.urlControl.markAsTouched();
      return;
    }

    this.creating = true;

    this.linkService.create(this.urlControl.value).subscribe({
      next: () => {
        this.creating = false;
        this.urlControl.reset();

        this.snackBar.open(
          'Short URL created.',
          'Close',
          { duration: 3000 }
        );

        this.loadLinks();
      },
      error: () => {
        this.creating = false;

        this.snackBar.open(
          'Could not create short URL.',
          'Close',
          { duration: 3000 }
        );
      }
    });
  }

  deleteLink(shortCode: string): void {
    this.linkService.delete(shortCode).subscribe({
      next: () => {
        this.links = this.links.filter(
          link => link.shortCode !== shortCode
        );

        this.snackBar.open(
          'Link deleted.',
          'Close',
          { duration: 3000 }
        );
      },
      error: () => {
        this.snackBar.open(
          'Could not delete link.',
          'Close',
          { duration: 3000 }
        );
      }
    });
  }

  getShortUrl(shortCode: string): string {
    return `http://localhost:8080/${shortCode}`;
  }
}