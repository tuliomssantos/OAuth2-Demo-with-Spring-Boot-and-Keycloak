import { getUser } from '@/lib/data/queries/get-user'
import { getUsers } from '@/lib/data/queries/get-users'
import { redirect } from 'next/navigation'

export default async function Page() {
  const user = await getUser()

  if (!user) {
    redirect('/login')
  }

  if (user.role !== 'ROLE_ADMIN') {
    return (
      <div>
        <p>You do not have permission to access this resource.</p>
      </div>
    )
  }

  const users = await getUsers()

  return (
    <div>
      <h1>Users</h1>
      <pre style={{ whiteSpace: 'pre-wrap' }}>
        {JSON.stringify(users, null, 2)}
      </pre>
    </div>
  )
}
