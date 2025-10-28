# Build and Run Instructions

## Prerequisites

- Docker Desktop installed and running
- Docker Compose installed

## Steps to Run

1. Start the database:

```bash
cd backend
docker compose up -d
```

2. Check the logs to ensure successful initialization:

```bash
docker compose logs db
```

3. Connect to database (optional, for troubleshooting):

```bash
docker compose exec db psql -U andre -d chatdb
```

## Database Details

- **Host**: localhost
- **Port**: 5432
- **Database**: chatdb
- **Username**: andre
- **Password**: pass

## Common Commands

- Start database: `docker compose up -d`
- Stop database: `docker compose down`
- View logs: `docker compose logs db`
- Reset data: `docker compose down -v && docker compose up -d`

## Troubleshooting

1. If database fails to start, check:

   - Docker is running
   - Port 5432 is not in use
   - Correct permissions on mounted volumes

2. To completely reset:

```bash
docker compose down -v  # Remove containers and volumes
docker compose up -d   # Start fresh
```

3. To check database connection:

```bash
docker compose exec db pg_isready -U andre -d chatdb
```
