import Link from 'next/link'

import { Button } from '@/components/ui/button'
import { GetAcmeResource } from '@/components/get-acme-resource'

import { getUser } from '@/lib/data/queries/get-user'
import LogoutButton from '@/components/logout-button'

export default async function Page() {
  const user = await getUser()

  console.log('user', user)

  return (
    <div className="flex justify-center items-center">
      <div className="max-w-[360px]">
        <h1 className="text-3xl font-semibold tracking-tight transition-colors mb-4">
          Please Sign In
        </h1>

        {!user ? (
          <Link href={'http://localhost:8081/oauth2/authorization/keycloak'}>
            <Button variant="outline" className="w-full">
              Login
            </Button>
          </Link>
        ) : (
          <LogoutButton />
        )}

        {user && (
          <div>
            <pre
              style={{
                whiteSpace: 'pre-wrap',
                wordBreak: 'break-all',
              }}
            >
              {JSON.stringify(user, null, 2)}
            </pre>
            <GetAcmeResource />
          </div>
        )}
      </div>
    </div>
  )
}

// import { redirect } from 'next/navigation'

// import { getUser } from '@/lib/data/queries/get-user'
// import Link from 'next/link'

// import LogoutButton from '@/components/logout-button'

// export default async function Page() {
//   const user = await getUser()

//   if (!user) {
//     redirect('/login')
//   }

//   return (
//     <div>
//       <h1>Home</h1>

//       <pre
//         style={{
//           whiteSpace: 'pre-wrap',
//           wordBreak: 'break-all',
//         }}
//       >
//         {JSON.stringify(user, null, 2)}
//       </pre>

//       {user.role === 'ROLE_ADMIN' && (
//         <div>
//           <Link href="/admin">Admin Dashboard</Link>
//         </div>
//       )}

//       <div>
//         <LogoutButton>Logout</LogoutButton>
//       </div>
//     </div>
//   )
// }
