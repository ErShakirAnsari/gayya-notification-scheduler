
# GAYYA ![Cron job status](https://api.cron-job.org/jobs/5788862/72f53e9e89299a39/status-7.svg)
[https://cron-job.org/en/](https://cron-job.org/en/)



## GitHub permissions for cron.org token:
`Read and Write access to actions`


```http request
curl --location 'https://api.github.com/repos/ErShakirAnsari/gayya-notification-scheduler/actions/workflows/daily-reminder.yml/dispatches' \
--header 'Accept: application/vnd.github+json' \
--header 'Authorization: token github_pat_xyz123' \
--header 'X-GitHub-Api-Version: 2022-11-28' \
--header 'Content-Type: application/json' \
--data '{"ref": "master" }'
```
